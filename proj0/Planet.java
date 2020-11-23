public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName; 

    /** Constructor to create planet from scratch. 
     *  @param  xxPos       x co-ordinate of the planet.
     *  @param  yyPos       y co-ordinate of the planet.
     *  @param  xxVel       x component of the planet's velocity.
     *  @param  yyVel       y component of the planet's velocity.
     *  @param  mass        The planet's mass.
     *  @param  imgFileName The file name to the image used for the planet.   
     */ 
    public Planet(double xxPos, double yyPos, double xxVel,
                  double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    /** Constructor to create a copy of a planet. 
     *  @param p The planet to be copied.
     */
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName; 
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p){
        double G = 6.67e-11;
        double r = this.calcDistance(p);
        double forceExerted = (G * this.mass * p.mass) / (r * r);

        return forceExerted;

    }
    public double calcForceExertedByX(Planet p){
        double forceExerted = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        double forceExertedX = forceExerted * dx / r;

        return forceExertedX;

    }
    
     public double calcForceExertedByY(Planet p){
        double forceExerted = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        double forceExertedY = forceExerted * dy / r;

        return forceExertedY;

    }

    public double calcNetForceExertedByX(Planet[] planetArray){
        double netForceX = 0;

        for (Planet p : planetArray){
            if (!this.equals(p)){
                netForceX += this.calcForceExertedByX(p);
            }
        }

        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] planetArray){
        double netForceY = 0;

        for (Planet p : planetArray){
            if (!this.equals(p)){
                netForceY += this.calcForceExertedByY(p);
            }
        }

        return netForceY;
}

    public void update(double dt,double fX,double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;

        this.xxVel = this.xxVel + aX * dt;
        this.yyVel = this.yyVel + aY * dt;

    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }


}