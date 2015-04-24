/*
 * TCSS305 - Autumn 2012
 * Assignment 3: SnapShop
 * Daniel M. Zimmerman
 */

package snapshop.filters;

import snapshop.image.PixelImage;

/**
 * An interface for filters that modify images.
 * 
 * @author Daniel M. Zimmerman
 * @author Marty Stepp
 * @version 1.0
 */
public interface Filter
{
  /** 
   * Modifies the image according to this filter's algorithm.
   * 
   * @param the_image The image.
   */
  void filter(PixelImage the_image);

  /** 
   * Returns the name of this filter. 
   * 
   * @return the name of this filter.
   */
  String getName();
}
