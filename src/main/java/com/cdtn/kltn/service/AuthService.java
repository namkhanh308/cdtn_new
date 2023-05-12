package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.ChangePasswordDTO;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.client.respone.ClientInfoResponse;
import com.cdtn.kltn.entity.AccountsLever;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.accountlever.AccountsLeverRepository;
import com.cdtn.kltn.repository.client.ClientRepository;
import com.cdtn.kltn.repository.image.ImageRepository;
import com.cdtn.kltn.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ImageRepository imageRepository;
    private final AccountsLeverRepository accountsLeverRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public BaseResponseData authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUserName(request.getEmail())
                .orElseThrow(() -> new StoreException("User not found by email: " + request.getEmail()));

        String role = userRepository.findUserRoleByUsername(user.getUsername());

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        user.setRole(role);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String jwtToken = jwtService.createToken(authentication);
        String jwtRefreshToken = jwtService.refreshToken(authentication);

        return (new BaseResponseData(200,"Đăng nhập thành công", AuthenticationResponse.builder().refreshToken(jwtRefreshToken).token(jwtToken).build()));
    }
    @Transactional
    public BaseResponseData registerUser(RegistrationDTO registrationDTO) {
        if (userRepository.findByUserName(registrationDTO.getEmail()).isPresent()) {
            return new BaseResponseData(500, "User đã tồn tại", null);
        }
        User userEntity = User.builder()
                .userName(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .role("ROLE_CLIENT")
                .statusAccount(Enums.Status.ACTIVE.getCode())
                .build();
        User user =  userRepository.save(userEntity);
        //khởi tạo client
        Client client = Client.builder().userId(user.getId())
                .typeLoan(Enums.LoanType.TENANT.getCode())
                .fullName(registrationDTO.getFirstName() + " " + registrationDTO.getLastName())
                .money("0")
                .build();
        Client client1 = clientRepository.save(client);
        client1.setCodeClient("client." + client1.getId().toString());
        clientRepository.save(client1);
        // Khởi tạo account_lever
        accountsLeverRepository.save(AccountsLever.builder()
                .accountTypeLever(Enums.TypeAccountLever.MIENPHI.getCode())
                .codeClient(client1.getCodeClient())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .countNewsUpload(Enums.TypeAccountLever.MIENPHI.getCountNewsUpload())
                .status(1)
                .build());
        return new BaseResponseData(200, "Đăng ký thành công", null);
    }
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO){
        User user = userRepository.findById(changePasswordDTO.getId())
                .orElseThrow(()-> new StoreException("Không tìn thấy user"));
        boolean isMatch = passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());
        if(!isMatch){
            throw new StoreException("Mật khẩu cũ không đúng");
        }else {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        }
    }

    @Transactional
    public BaseResponseData clientInfo(String token) {
        String email = jwtService.getUserNameByToken(token);
        Optional<User> user = userRepository.findByUserName(email);
        if (user.isEmpty()) {
            return new BaseResponseData(500, "User không tồn tại", null);
        }else {
            Optional<Client> client = clientRepository.findByUserId(user.get().getId());
            if(client.isEmpty()){
                return new BaseResponseData(500, "Client không tồn tại", null);
            }else {
                Optional<Image> image = imageRepository.findByCodeClientAndLevel(client.get().getCodeClient(),1);
                Optional<AccountsLever> accountsLever = accountsLeverRepository.findByCodeClient(client.get().getCodeClient());
                if(accountsLever.isEmpty()){
                    return new BaseResponseData(500, "Cấp tài khoản không tồn tại", null);
                }else {
                    return image.map(value -> new BaseResponseData(200, "Dữ liệu client được trả ra thành công",
                            ClientInfoResponse.builder()
                                    .codeClient(client.get().getCodeClient())
                                    .userId(client.get().getUserId())
                                    .fullName(client.get().getFullName())
                                    .provinceCode(client.get().getProvinceCode())
                                    .districtCode(client.get().getDistrictCode())
                                    .wardsCode(client.get().getWardsCode())
                                    .introduces(client.get().getIntroduces())
                                    .phone(client.get().getPhone())
                                    .typeLoan(client.get().getTypeLoan())
                                    .money(client.get().getMoney())
                                    .passport(client.get().getPassport())
                                    .firstName(user.get().getFirstName())
                                    .lastName(user.get().getLastName())
                                    .url(value.getUrl())
                                    .accountTypeLever(accountsLever.get().getAccountTypeLever())
                                    .accountLeverTypeName(Enums.TypeAccountLever.checkName(accountsLever.get().getAccountTypeLever()))
                                    .statusAccountLever(accountsLever.get().getStatus())
                                    .build())).orElseGet(() -> new BaseResponseData(200, "Dữ liệu client được trả ra thành công",
                            ClientInfoResponse.builder()
                                    .codeClient(client.get().getCodeClient())
                                    .userId(client.get().getUserId())
                                    .fullName(client.get().getFullName())
                                    .provinceCode(client.get().getProvinceCode())
                                    .districtCode(client.get().getDistrictCode())
                                    .wardsCode(client.get().getWardsCode())
                                    .introduces(client.get().getIntroduces())
                                    .phone(client.get().getPhone())
                                    .typeLoan(client.get().getTypeLoan())
                                    .money(client.get().getMoney())
                                    .passport(client.get().getPassport())
                                    .firstName(user.get().getFirstName())
                                    .lastName(user.get().getLastName())
                                    .accountTypeLever(accountsLever.get().getAccountTypeLever())
                                    .accountLeverTypeName(Enums.TypeAccountLever.checkName(accountsLever.get().getAccountTypeLever()))
                                    .statusAccountLever(accountsLever.get().getStatus())
                                    .build()));
                }
            }
        }
    }


}
