package com.modules.image;


public class SimpleSample {

	public static void main(String[] args) {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    new SimpleSample().run();
	}

	public void run() {
		System.out.println("\nRunning Image detection");

		String haarDir = "/usr/local/Cellar/opencv/2.4.5/share/OpenCV/haarcascades";
		String lbpDir = "/usr/local/Cellar/opencv/2.4.5/share/OpenCV/lbpcascades";
//		CascadeClassifier faceDetector = new CascadeClassifier(lbpDir + "/lbpcascade_frontalface.xml");
//		CascadeClassifier faceDetector = new CascadeClassifier(haarDir + "/haarcascade_frontalface_alt.xml");
//		CascadeClassifier faceDetector = new CascadeClassifier(haarDir + "/haarcascade_upperbody.xml");
//		CascadeClassifier faceDetector = new CascadeClassifier(haarDir + "/haarcascade_mcs_upperbody.xml");
//		
//		
//		File directory = new File("/Users/estebanroblesluna/Desktop/fotos");
//		
//		for (File imageFile : directory.listFiles()) {
//			try {
//				Mat image = Highgui.imread(imageFile.getAbsolutePath());
//
//				MatOfRect faceDetections = new MatOfRect();
//				faceDetector.detectMultiScale(image, faceDetections);
//
//				System.out.println(String.format("Detected %s faces",
//						faceDetections.toArray().length));
//
//				// Draw a bounding box around each face.
//				for (Rect rect : faceDetections.toArray()) {
//					Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
//							+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
//				}
//
//				// Save the visualized detection.
//				String filename = new File(directory, "det-" + imageFile.getName()).getAbsolutePath();
//				System.out.println(String.format("Writing %s", filename));
//				Highgui.imwrite(filename, image);		
//			} catch (Exception e) {
//				
//			}
//	
//		}
	}
}
