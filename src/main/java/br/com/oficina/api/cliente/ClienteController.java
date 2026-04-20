package br.com.oficina.api.cliente;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("/clientes")
public class ClienteController {

    public ResponseEntity<String> listar() {
        return ResponseEntity.ok().build();
    }
}
