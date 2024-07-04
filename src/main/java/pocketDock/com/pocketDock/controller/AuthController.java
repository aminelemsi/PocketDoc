package pocketDock.com.pocketDock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pocketDock.com.pocketDock.config.CloudinaryService;
import pocketDock.com.pocketDock.dto.ForgetPasswordRequest;
import pocketDock.com.pocketDock.dto.ReqRes;
import pocketDock.com.pocketDock.dto.ResetPasswordRequest;
import pocketDock.com.pocketDock.entity.OurUsers;
import pocketDock.com.pocketDock.repository.OurUserRepo;
import pocketDock.com.pocketDock.service.AuthService;
import pocketDock.com.pocketDock.service.UserService;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private AuthService authService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    CloudinaryService cloudinaryImageService ;



    @PostMapping("/all/signup")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/all/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signIpRequest){
        return  ResponseEntity.ok(authService.signIn(signIpRequest));
    }
    @PostMapping("/all/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return  ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.deleteUser(id));
    }
    @PutMapping("/all/users/{id}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer id, @RequestBody ReqRes updateUserRequest) {
        return ResponseEntity.ok(authService.updateUser(id, updateUserRequest));
    }
    @GetMapping("/admin/users")
    public ResponseEntity<List<OurUsers>> getAllUsers() {
        List<OurUsers> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all/users/{id}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Integer id) {
        ReqRes user = authService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }

    }
    @PostMapping("/all/forgot-password")
    public ResponseEntity<ReqRes> forgotPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        // Utilisez l'email contenu dans forgetPasswordRequest pour la réinitialisation du mot de passe
        String email = forgetPasswordRequest.getEmail();

        // Effectuez les opérations nécessaires pour réinitialiser le mot de passe et envoyer l'email de réinitialisation
        String resetToken = authService.generateResetToken();
        Optional<OurUsers> userOptional = ourUserRepo.findByEmail(email);
        OurUsers user = userOptional.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        user.setResetToken(resetToken);
        ourUserRepo.save(user);
        authService.sendPasswordResetEmail(email, resetToken);

        // Répondre avec un message indiquant que l'e-mail a été envoyé
        ReqRes response = new ReqRes();
        response.setMessage("Password reset email sent");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/all/reset-password")
    public ResponseEntity<ReqRes> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String newPassword = resetPasswordRequest.getNewPassword();

        // Mettre à jour le mot de passe de l'utilisateur avec le nouveau mot de passe
        // (vous devrez implémenter cette méthode dans AuthService)
        authService.resetPassword(token, newPassword);

        // Répondre avec un message indiquant que le mot de passe a été réinitialisé
        ReqRes response = new ReqRes();
        response.setMessage("Password reset successfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/all/profileimage")
    public ResponseEntity<String> uploadProfileImageByEmail(@RequestParam("email") String email, @RequestParam("profileImage") MultipartFile profileImage) {
        try {
            // Télécharger l'image de profil et obtenir son URL
            String profileImageUrl = cloudinaryImageService.uploadProfileImage(profileImage);

            // Mettre à jour l'URL de l'image de profil dans la base de données pour l'utilisateur correspondant
            authService.updateProfileImage(email, profileImageUrl);

            // Retourner l'URL de l'image de profil comme corps de la réponse
            return ResponseEntity.ok(profileImageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading profile image");
        }

    }

    @PostMapping("/all/profileimage1")
    public ResponseEntity<String> uploadProfileImageByEmail1(@RequestParam("email") String email, @RequestParam("profileImage1") MultipartFile profileImage1) {
        try {
            // Télécharger l'image de profil et obtenir son URL
            String profileImageUrl1 = cloudinaryImageService.uploadProfileImage(profileImage1);

            // Mettre à jour l'URL de l'image de profil dans la base de données pour l'utilisateur correspondant
            authService.updateProfileImage1(email, profileImageUrl1);

            // Retourner l'URL de l'image de profil comme corps de la réponse
            return ResponseEntity.ok(profileImageUrl1);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading profile image");
        }}
    @GetMapping("/admin/verified/count")
    public ResponseEntity<Double> countVerifiedUsers() {
        double count = authService.countVerifiedUsers();
        return ResponseEntity.ok(count);
    }


    @GetMapping("/admin/age/percentage")
    public ResponseEntity<double[]> calculateAgePercentage() {
        double[] percentages = authService.calculateAgePercentage();
        return ResponseEntity.ok(percentages);
    }

    @PutMapping("/admin/{userId}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable Integer userId, @RequestParam Integer newStatus) {
        ReqRes response = authService.updateUserStatus(userId, newStatus);
        if (response.getStatusCode() == 200) {
            return ResponseEntity.ok(response.getMessage());
        } else if (response.getStatusCode() == 404) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.getError());
        }
    }
    
    @GetMapping("/all/user/{email}")
    public OurUsers getUserByEmail(@PathVariable String email) {
        Optional<OurUsers> user = ourUserRepo.findByEmail(email);
        return user.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    @PutMapping("/admin/role/{id}")
    public ResponseEntity<ReqRes> updaterole(@PathVariable Integer id, @RequestBody ReqRes updateUserRequest) {
        return ResponseEntity.ok(authService.roleUser(id, updateUserRequest));
    }
    @GetMapping("/all/get-name/{userId}")
    public String getName(@PathVariable int userId){
        return ourUserRepo.findUsersById(userId).getName()+" "+ourUserRepo.findUsersById(userId).getLastname();
    }
}




