package carato.carato_backend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequiredArgsConstructor
public class ImagesController {

    @Value("${imagesDir}")
    private String imagesDir;

    @GetMapping(value = "/{imageName}", produces = MediaType.ALL_VALUE)
    public @ResponseBody Resource getImageName(@PathVariable String imageName) {

        File file = new File(imagesDir + "/" + imageName);

        return new FileSystemResource(file);
    }
}