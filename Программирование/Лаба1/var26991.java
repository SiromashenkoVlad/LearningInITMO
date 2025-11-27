import java.util.Random;

public class var26991 {
        public static void main(String[] args) {
		// создание массива из long-ов w
                long[] w;
		w = new long[8];
		for (int i = 0; i < 8; i++){
			w[i] = 18 - 2 * i;
		}
		
		// создание массива из float x
		float[] x;
		x = new float[16];
		for (int i = 0; i < 16; ++i){
			x[i] = new Random().nextFloat() * 24.0f - 9.0f;
		}

		// создание двумерного массива w1
		double[][] w1 = new double[8][16];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 16; j++){
				double valx = x[j];
				w1[i][j] = calcArrayW(w, valx, i);
			}
		}
		printArray(w1);
        }


	private static double calcArrayW(long[] w, double valx, int i){
		switch((int)w[i]) {
			case 8: {
				double chislit = Math.pow(valx / 0.25, valx);
 				double znam = 1.0 / 3 - Math.pow(valx * (valx + 1.0 / 2), valx);
                        	return Math.cos(Math.pow(chislit / znam, 2));
			}
			case 6, 14, 16, 18: {
				double powx = Math.pow(4 * (2.0 / 3 + valx), valx);
				double sn = Math.sin(Math.cos(powx));
				return sn;	
			}
			default: {
				 double powx = Math.pow((valx + 3) / 24, 2);
				 double sn = Math.sin(Math.atan(powx));
				 return sn;
			} 
		}/*
		if (w[i] == 8){
			double chislit = Math.pow(valx / 0.25, valx);
			double znam = 1.0 / 3 - Math.pow(valx * (valx + 1.0 / 2), valx);
			return Math.cos(Math.pow(chislit / znam, 2));
		}
		else if (w[i] == 6 || w[i] == 14 || w[i] == 16 || w[i] == 18){
			double powx = Math.pow(4 * (2.0 / 3 + valx), valx);
			double sn = Math.sin(Math.cos(powx));
			return sn;
		}
		else {
			double powx = Math.pow((valx + 3) / 24, 2);
			double sn = Math.sin(Math.atan(powx));
			return sn;
		}*/
	}


	// Статический метод для вывода массива
	private static void printArray(double[][] array) {
		System.out.println("=".repeat(80));

		for (int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++) {
				System.out.printf("%10.5f ", array[i][j]);
			}
			System.out.println();
		}
		System.out.println("=".repeat(80));
	}
} 

