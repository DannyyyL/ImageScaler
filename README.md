# ImageScalar

A small Java Swing GUI that visualizes and scales images using a quadtree representation.

![Demo](./ImageScalar.gif)

What it does
- Builds a quadtree from an input image and renders it on a 512×512 canvas.
- Lets you zoom in/out by changing displayed resolution (++Res / --Res).
- Click to select coordinates, then use Find to center on that spot.
- Shows marked pixels and average-color blocks computed from the quadtree.

Quick run
- Compile and run with a single image file:
  javac *.java && java Gui path/to/image.png

Controls (GUI)
- ++Res / --Res — increase / decrease displayed detail level
- Find — center on the last clicked point
- Click on the image to set the target coordinate
- Exit — close the app
