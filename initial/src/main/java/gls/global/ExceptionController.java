package gls.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
//    @RequestMapping("/exception")
//    public void catchException() {
//        throw new RuntimeException("error occur");
//    }
	
	  @GetMapping("/exception")
	  public DefaultException permissionException() {
		  return DefaultException.Default();
	  }
}