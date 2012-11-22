/* 
 * This file is part of Quelea, free projection software for churches.
 * 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.windows.newsong;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.quelea.data.ImageBackground;
import org.quelea.data.ThemeDTO;
import org.quelea.languages.LabelGrabber;
import org.quelea.services.utils.FileFilters;
import org.quelea.services.utils.LoggerUtils;
import org.quelea.windows.main.DisplayCanvas;
import org.quelea.windows.lyrics.LyricDrawer;
import org.quelea.windows.main.QueleaApp;

/**
 * The image button where the user selects a image.
 * @author Michael
 */
public class ImageButton extends Button {

    private static final Logger LOGGER = LoggerUtils.getLogger();
    private String imageLocation;
    private final FileChooser fileChooser;

    /**
     * Create and initialise the image button.
     * @param imageLocationField the image location field that goes with this
     * button.
     * @param canvas the preview canvas to update.
     */
    public ImageButton(final TextField imageLocationField, final DisplayCanvas canvas) {
        super(LabelGrabber.INSTANCE.getLabel("select.image.button"));
        fileChooser = new FileChooser();
        final File imageDir = new File("img");
        fileChooser.setInitialDirectory(imageDir);
        fileChooser.getExtensionFilters().add(FileFilters.IMAGES);
        setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent t) {
                File selectedFile = fileChooser.showOpenDialog(QueleaApp.get().getMainWindow());
                if(selectedFile != null) {
                    File newFile = new File(imageDir, selectedFile.getName());
                    try {
                        if(!selectedFile.getCanonicalPath().startsWith(imageDir.getCanonicalPath())) {
                            FileUtils.copyFile(selectedFile, newFile);
                        }
                    }
                    catch(IOException ex) {
                        LOGGER.log(Level.WARNING, "", ex);
                    }

                    imageLocation = imageDir.toURI().relativize(newFile.toURI()).getPath();
                    imageLocationField.setText(imageLocation);
                    LyricDrawer drawer = new LyricDrawer(canvas); //@todo check  if here should be theme setting
                
                    drawer.setTheme(new ThemeDTO(drawer.getTheme().getFont(),
                            drawer.getTheme().getFontPaint(), new ImageBackground(imageLocation),
                            drawer.getTheme().getShadow()));
                }
            }
        });
    }

    /**
     * Get the location of the selected image.
     * @return the selected image location.
     */
    public String getImageLocation() {
        return imageLocation;
    }
}
