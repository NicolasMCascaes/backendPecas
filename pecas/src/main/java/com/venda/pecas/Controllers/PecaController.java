package com.venda.pecas.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venda.pecas.Repositories.PecasRepository;

@RestController
@RequestMapping("/pecas")
public class PecaController {
    @Autowired
    private PecasRepository pecasRepository;
}
