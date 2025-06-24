package com.starbuks.app.usecase;

import com.starbuks.app.entitys.bean.Compra;
import java.util.List;

public interface CompraUseCase {
    List<Compra> obtenerComprasPorUsuario(Long usuarioId);
}