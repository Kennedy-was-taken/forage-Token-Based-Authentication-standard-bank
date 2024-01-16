package com.JwtAuthentication.TaskOne.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResponse<T> {
    
    public T Data;

    public boolean Success = true;

    public String Message;

}
