/** The class used to run the NBody simulation.
 *  @author Arjun Nair 
 */
public class NBody {
 /** Returns the radius of the universe 
     *  by reading it from the file.
     *  @param  fileName    The file which needs to be read from.
     */
    public static double readRadius(String fileName) {
        In fileHandle = new In(fileName); 
        
        int numPlanets = fileHandle.readInt();
        double radius = fileHandle.readDouble();
        
        return radius;
    }

    /** Returns an array of planets corresponding to the file.
     *  @param  fileName    The file which needs to be read from.
     */
    public static Planet[] readPlanets(String fileName) {
        In fileHandle = new In(fileName);

        int numPlanets = fileHandle.readInt();
        double radius = fileHandle.readDouble();
        
        Planet[] planetArray = new Planet[numPlanets];

        for (int i = 0; i < numPlanets; i++) {
            double xxPos = fileHandle.readDouble();
            double yyPos = fileHandle.readDouble();
            double xxVel = fileHandle.readDouble();
            double yyVel = fileHandle.readDouble();
            double mass = fileHandle.readDouble();
            String imgFileName = fileHandle.readString();

            planetArray[i] = new Planet(xxPos, yyPos, xxVel,
                                        yyVel, mass, imgFileName);
        }
        return planetArray;
    }

    /** Defines the rules for the simulation */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]); 
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planetArray = readPlanets(fileName);

        // setting the scale 
        StdDraw.setScale(-radius, radius);

        // setting the music
        String spaceOdysseyPath = "./audio/2001.mid";
        StdAudio.play(spaceOdysseyPath);

        // setting the background
        String backgroundImgPath = "./images/starfield.jpg";
        StdDraw.picture(0, 0, backgroundImgPath);

        
        //simulation loop
        StdDraw.enableDoubleBuffering();
        
        double currentTime = 0;
        int numPlanets = planetArray.length;

        while (currentTime < T){
            double[] xForces = new double[numPlanets];
            double[] yForces = new double[numPlanets];


            for (int i=0; i < numPlanets; i++){
                double xForce = planetArray[i].calcNetForceExertedByX(planetArray);
                double yForce = planetArray[i].calcNetForceExertedByY(planetArray);

                xForces[i] = xForce;
                yForces[i] = yForce;
            }
            
            for (int i = 0; i < numPlanets; i++){
                planetArray[i].update(dt, xForces[i],yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, backgroundImgPath);


            for (Planet p: planetArray){
                p.draw();
            }

           
            StdDraw.show();
            StdDraw.pause(10);
            currentTime += dt;
        }
        StdOut.printf("%d\n", numPlanets);
        StdOut.printf("%.2e\n", radius);

        for (int i = 0; i < numPlanets; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planetArray[i].xxPos, planetArray[i].yyPos, 
                          planetArray[i].xxVel, planetArray[i].yyVel, 
                          planetArray[i].mass,  planetArray[i].imgFileName);   
        }
    }

}
