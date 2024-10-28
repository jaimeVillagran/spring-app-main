package com.course.app_medic.security;

// Record: se encarga de devolver el token en una respuesta http
public record JwtResponse(String jwtToken) {
}
