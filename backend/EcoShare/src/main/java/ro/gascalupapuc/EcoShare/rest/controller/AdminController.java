package ro.gascalupapuc.EcoShare.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gascalupapuc.EcoShare.model.dto.ResponseUserDTO;
import ro.gascalupapuc.EcoShare.model.dto.UserDTO;
import ro.gascalupapuc.EcoShare.rest.service.AdminService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AdminController {
    private final AdminService adminService;
    @PostMapping("")
    void createAccount(){
    }

    @PostMapping("admin/creteUser")
    ResponseEntity<ResponseUserDTO> createUser(@RequestParam String role,
                                               @RequestBody UserDTO userDTO){
       return ResponseEntity.ok(adminService.createUser(role, userDTO));
    }
}
