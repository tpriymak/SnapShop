/**
 * @author Mahir Ahmed
 * @version 23
 */

package snapshop.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import snapshop.filters.EdgeDetectFilter;
import snapshop.filters.EdgeHighlightFilter;
import snapshop.filters.Filter;
import snapshop.filters.FlipHorizontalFilter;
import snapshop.filters.FlipVerticalFilter;
import snapshop.filters.GrayscaleFilter;
import snapshop.filters.SharpenFilter;
import snapshop.filters.SoftenFilter;
import snapshop.image.PixelImage;

/** 
 * @author Mahir Ahmed
 * @version 23 Snapshop 
 * GUI that will allow the user to edit an image.
 */
@SuppressWarnings("serial")
public class SnapShopFrame extends JFrame
{
  /**
   * array that will hold all the filters used in the editor.
   */
  private final Filter[] my_filters = new Filter[] {new EdgeDetectFilter(),
    new EdgeHighlightFilter(), new FlipHorizontalFilter(), new FlipVerticalFilter(),
    new GrayscaleFilter(), new SharpenFilter(), new SoftenFilter()};
  /**
   * field that will hold the picture.
   */
  private PixelImage my_pixel_image;
  /**
   * Array list that stores the buttons.
   */
  private final ArrayList<JButton> my_buttons = new ArrayList<JButton>();
  /**
   * field that holds the image text.
   */
  private final JLabel my_pic_label;

  /**
   * field that holds the file chosen.
   */
  private final JFileChooser my_file;

  /**
   * constructor that puts the name of the window. also initializes the file and
   * window label.
   */
  public SnapShopFrame()
  {
    super("TCSS 305 SnapShop - Winter 2013");
    my_pic_label = new JLabel();
    my_file = new JFileChooser();
  }

  /**
   * method that initializes the filter buttons.
   * 
   * @return the panel of filter buttons.
   */
  private JPanel filterButtons()
  {
    final JPanel filter_panel = new JPanel();
    final FlowLayout panel_layout = new FlowLayout();
    filter_panel.setLayout(panel_layout);
    for (int i = 0; i < my_filters.length; i++)
    {
      filter_panel.add(createButton(my_filters[i]));
    }
    return filter_panel;
  }

  /**
   * method that has the open and save as button.
   * 
   * @return the buttons on a specified location.
   */
  private JPanel optionButtons()
  {
    final JPanel option_panel = new JPanel();
    final JButton open_button = new JButton("Open...");
    open_button.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_file.showOpenDialog(getParent());
        try
        {
          my_pixel_image = PixelImage.load(my_file.getSelectedFile());
          my_pic_label.setIcon(new ImageIcon(my_pixel_image));
          for(int i = 0; i < my_buttons.size(); i++)
          {
            my_buttons.get(i).setEnabled(true);
          }
          pack();
        }
        catch (final IOException e)
        {
          JOptionPane.showMessageDialog(null, "File did not contain a valid image: " + my_file);
        }
        catch (final IllegalArgumentException e)
        {
          
        }
      }
    });

    final JButton save_button = new JButton("Save...");
    save_button.setEnabled(false);
    my_buttons.add(save_button);
    save_button.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_file.showSaveDialog(getParent());
        try
        {
          my_pixel_image.save(my_file.getSelectedFile());
        }
        catch (final IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    final JButton close_image = new JButton("Close Image...");
    close_image.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent the_event)
      {
        my_pic_label.setIcon(null);
        my_pixel_image = null;
        for(int i = 0; i < my_buttons.size(); i++)
        {
          my_buttons.get(i).setEnabled(false);
        }
        pack();
      }
    });

    option_panel.add(open_button);
    option_panel.add(save_button);
    option_panel.add(close_image);

    return option_panel;
  }

  /**
   * method that creates the buttons.
   * 
   * @param the_filter passes the name of the filter as a parameter.
   * @return the button name.
   */
  private JButton createButton(final Filter the_filter)
  {
    // Look this up!
    final JButton button = new JButton(the_filter.getName());
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        the_filter.filter(my_pixel_image);
        my_pic_label.setIcon(new ImageIcon(my_pixel_image));
      }
    });
    my_buttons.add(button);
    button.setEnabled(false);
    return button;
  }

  /**
   * method that will display the window on the screen.
   */
  public void start()
  {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    add(filterButtons(), BorderLayout.NORTH);
    add(my_pic_label, BorderLayout.CENTER);
    add(optionButtons(), BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }
}