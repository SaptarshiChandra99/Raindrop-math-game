package RainDropMaths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDirectory {
    String backButtonIconPath="F:\\firstjavaprogram\\src\\AnimationTests\\back.png";

    String mainMenuSkyPath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\sky.png";
    String mainMenuCloudsPath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\cloudBG.png";

    String gameRainyCloudPath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\cloud31.png";
    String gameWaveImagePath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\sea_waves scaled.png";

    String sunImgPath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\Sun.png";
    String gameOverBeachBgPath="F:\\firstjavaprogram\\src\\RainDropMaths\\Images\\beach.png";

    String rulesBeachWithSunBgPath="F:\\firstjavaprogram\\src\\AnimationTests\\beachWithSun.png";


    private BufferedImage getImage(String path){
        BufferedImage image=null;
        try {
            image= ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Image not available in the given path");
        }
        return image;
    }

    public BufferedImage getBackButtonImage() { return getImage(backButtonIconPath);    }

    public BufferedImage getMainMenuCloudsImage() { return getImage(mainMenuCloudsPath);    }

    public BufferedImage getMainMenuSkyImage() { return getImage(mainMenuSkyPath);    }

    public BufferedImage getGameRainyCloudImage() { return getImage(gameRainyCloudPath);    }

    public BufferedImage getGameSeaWavesImage() { return getImage(gameWaveImagePath);    }

    public BufferedImage getGameOverSunImage() { return getImage(sunImgPath);    }

    public BufferedImage getGameOverBeachBGImage() { return getImage(gameOverBeachBgPath);    }

    public BufferedImage getRulesBeachwithSunBGImage() { return getImage(rulesBeachWithSunBgPath);    }


}
