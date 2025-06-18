package com.DemoRefri.demoRefri.enums;

public enum EstadoEnvio {
    PENDIENTE,      // 🟡 El envío aún no ha sido despachado
    EN_TRANSITO,    // 🚚 El paquete está en camino
    ENTREGADO,      // ✅ El paquete ha sido entregado al cliente
    CANCELADO       // ❌ El envío ha sido cancelado
}