package com.java.cadastro_usuario.business;


import com.java.cadastro_usuario.infrastructure.entitys.Usuario;
import com.java.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private  final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    public void save(Usuario usuario){
        repository.saveAndFlush(usuario);
    }

    public Usuario buscarUserEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado")
        );
    }

    @Transactional
    public void deleteUserEmail(String email){
        repository.deleteByEmail(email);
    }


    public void attUser(Integer id, Usuario usuario){
        Usuario usuarioEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuario não encontrado"));
        Usuario usuarioAtualizado = Usuario.builder()
                .email(usuario.getEmail() != null ? usuario.getEmail() : usuarioEntity.getEmail())
                .nome(usuario.getNome() != null ? usuario.getNome() : usuarioEntity.getNome())
                .id((usuarioEntity.getId()))
                .build();

        repository.saveAndFlush(usuarioAtualizado);
    }
}
