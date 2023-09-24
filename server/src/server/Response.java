package server;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 99808453L;
    
    public String message;
    public Object object;
}
