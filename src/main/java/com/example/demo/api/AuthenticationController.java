package com.example.demo.api;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.userService;
import com.example.demo.tool.AuthToken;
import com.example.demo.tool.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private userService userService;

    @RequestMapping(value= "/prueba",method=RequestMethod.POST)
    @ResponseBody
    public String prueba(@RequestBody Role rol){
        return rol.getRol();
    }
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<AuthToken> register(@RequestBody User loginUser) throws AuthenticationException {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(loginUser.getUserName()+"  "+loginUser.getPassword());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUserName());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getUserName()));
    }

}

