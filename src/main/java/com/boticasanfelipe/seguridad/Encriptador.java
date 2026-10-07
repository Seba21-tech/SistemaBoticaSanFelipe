package com.boticasanfelipe.seguridad;

import java.security.MessageDigest;

public class Encriptador {
    public static String encriptarSHA256(String texto) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes("UTF-8"));

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error al encriptar", ex);
        }
        return hexString.toString();
    }
}
