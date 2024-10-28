package ddalpi.attacker.service;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private int image = 1;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
