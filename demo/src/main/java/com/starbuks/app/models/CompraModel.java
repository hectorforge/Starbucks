package com.starbuks.app.models;

import com.starbuks.app.entitys.bean.Compra;
import com.starbuks.app.persistence.CompraRepository;
import com.starbuks.app.usecase.CompraUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraModel implements CompraUseCase {

    private final CompraRepository compraRepository;

    @Override
    public List<Compra> obtenerComprasPorUsuario(Long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }
}