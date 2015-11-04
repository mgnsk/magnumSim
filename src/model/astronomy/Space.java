package model.astronomy;

import main.Config;
import model.maths.Point3D;
import model.maths.Vector3D;

import java.util.Random;

public class Space implements Config {
    //
    // public float hourofday = 0f;
    // public float dayofyear = 0f;
    // public float dayofmonth = 10f;

    private Planet[] planets;

    static float G = 0.001f; // TODO: change this

    // public float[][] colors;
    private int realNumPlanets;

    /**
     * Class constructor
     */
    public Space() {
        this.realNumPlanets = Config.numPlanets;

        Random rand = new Random();

        this.planets = new Planet[Config.numPlanets];

        for (int i = 0; i < Config.numPlanets; i++) {
            int randomX = rand.nextInt((20));
            int randomY = rand.nextInt((10));
            int randomZ = rand.nextInt((15));

            float mul = 1;
            if (i % 3 == 0) {
                mul = (float) Math.random();

            } else {
                mul = -1;
            }

            Vector3D pos = new Vector3D((float) Math.sin(randomX ^ 2), (float) Math.cos(randomY), (float) randomZ * mul);
            Vector3D vel = new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random());

            //Vector3D pos = new Vector3D();
            // Vector3D vel = new Vector3D();
            Vector3D force = new Vector3D();

            double massRadius = Math.random();

            this.planets[i] = new Planet(massRadius, massRadius, pos, vel, force);
            this.planets[i].passes = 0;

            // TODO: random colors atm, make some classes orsmth
            this.planets[i].mass *= 100;
            this.planets[i].pos.multiply(100);
            this.planets[i].vel.multiply(5);
            //  this.planets[i].pos.multiply(10);

        }

    }

    /**
     * This method is called from <code>Main.display()</code> every frame.
     * It iterates through all planets and calculates the forces applied to them
     * @param dt Time passed since the last frame
     */
    public void simulate(double dt) {
        /*
         * Basically we need to iterate through all the planets and calculate
         * their new position and velocity vectors according to the force
         * applied to them
         */

        for (int i = 0; i < this.realNumPlanets; i++) {

            if (this.planets[i] != null) {

                // Find all gravitational forces which attract a single planet
                for (int j = 0; j < this.realNumPlanets; j++) {
                    if (i != j && this.planets[j] != null) {

                        double distanceVectorX = this.planets[j].pos.x - this.planets[i].pos.x;
                        double distanceVectorY = this.planets[j].pos.y - this.planets[i].pos.y;
                        double distanceVectorZ = this.planets[j].pos.z - this.planets[i].pos.z;

                        Vector3D distanceVector = new Vector3D(distanceVectorX, distanceVectorY, distanceVectorZ);

                        double forceBetween = (G * this.planets[i].mass * this.planets[j].mass)
                                / (Math.pow(distanceVector.length(), 2));

                        Vector3D unitVector = distanceVector.getUnitVector();

                        // if the planets are far enough (not side by side)
                        if (distanceVector.length() > (this.planets[i].radius + this.planets[j].radius)) {
                            Vector3D forceVector = unitVector.multiply(forceBetween);

                            planets[i].applyForce(forceVector);

                            this.planets[i].solve(dt);
                        } else {

                            //System.out.println("coll");

                            //TODO write methods
                            //Below comment merges planets as they touch
                        /*    this.planets[i].mass += this.planets[j].mass;
                            Vector3D pos1 = new Vector3D(this.planets[i].pos);
                            Vector3D pos2 = new Vector3D(this.planets[j].pos);

                            pos1.multiply(this.planets[i].mass);
                            pos2.multiply(this.planets[j].mass);

                            this.planets[i].pos = pos1.add(pos2).divide(this.planets[i].mass + this.planets[j].mass);
                            this.planets[i].vel.add(this.planets[j].vel);
                            this.planets[i].radius += this.planets[j].radius;
                            this.planets[i].mass += this.planets[j].mass;

                            if (this.realNumPlanets > 1) {
                                this.realNumPlanets--;
                            }
                            for (int k = j; k < this.planets.length - 1; k++) {

                                 this.planets[k] = this.planets[k + 1];
                            }*/

                            // we have a collision
                            // va1 = ( v1 * (m1 - m2) + 2 * m2 * v2 ) / ( m1 + m2 )
                            ////
                            //
                            // planets[i].force.multiply(-1);
                            // planets[j].force.multiply(-1);
                            // v1f = −v1i + 2(m1 v1i + m2 v2i)
                            // m1 + m2
                            // (1)
                            //
                            // Vector3D v1 = new Vector3D(planets[i].vel);
                            // Vector3D v2 = new Vector3D(planets[j].vel);
                            //
                            //
                            // Vector3D murd1 = v1.multiply(planets[i].mass);
                            // murd1.add(v2.multiply(planets[j].mass));
                            // murd1.multiply(2);
                            // murd1.divide(planets[i].mass + planets[j].mass);
                            //
                            // v1 = new Vector3D(planets[i].vel);
                            // v1.multiply(-1);
                            //
                            // v2 = new Vector3D(planets[j].vel);
                            // v2.multiply(-1);
                            // Vector3D changedVel1 = v1.add(murd1);
                            // Vector3D changedVel2 = v2.add(murd1);
                            //
                            // System.out.println(changedVel1.x);
                            //
                            //
                            // planets[i].vel = changedVel1;
                            // planets[j].vel = changedVel2;
                            // this.planets[j].solve(dt);
                            // v2f = −v2i + 2(m1 v1i + m2 v2i)
                            // m1 + m2
                            //
                            //
                            //
                            //
                            //
                            //
                            //
                        }


                    }
                }
            }
        }

        // Here the calculations are made, time to draw to the screen
        for (int i = 0; i < this.realNumPlanets; i++) {
            if (this.planets[i] != null) {
                this.planets[i].drawOnScreen();
            }
        }


     //   Point3D heaviestPlanetPos = this.getHeaviestPlanet();

        //System.out.println(heaviestPlanetPos.x);

     /*   Camera.center = heaviestPlanetPos;
        Camera.eye = heaviestPlanetPos.multiply(3);
*/

        // Vector3D camVector = new Vector3D(Camera.center, Camera.eye);

  /*      Point3D newPoint = this.getCenterOfMass();

        Vector3D movement = new Vector3D(newPoint, Camera.center);

        Camera.center = newPoint;*/
        // Camera.eye.add(movement);

        //Camera.vector = new Vector3D(Camera.center, Camera.eye);

/*
        Point3D center = this.getHeaviestPlanet();

        Camera.center = center;

        Point3D centerCopy = new Point3D(center);

        Camera.eye = centerCopy.multiply(10);*/
    }

    /**
     * @return The position of the heaviest planet
     */
    public Vector3D getHeaviestPlanet() {
        double mass = 0;
        Vector3D pos = null;


        for (int i = 0; i < this.realNumPlanets; i++) {
            if (this.planets[i].mass > mass) {
                mass = this.planets[i].mass;
                pos = new Vector3D(this.planets[i].pos);
            }

        }

        return pos;
    }

    public Vector3D getCenterOfMass() {
        Vector3D allPoints = new Vector3D();

        float allMasses = 0;

        for (int i = 0; i < this.realNumPlanets; i++) {
            // allMasses += this.planets[i].vel.length();

            if (this.planets[i] != null) {
                //TODO maybe exceptions here
                allMasses += this.planets[i].mass;

                Vector3D pos = new Vector3D(this.planets[i].pos);

                pos.multiply(this.planets[i].mass);
                allPoints.add(pos);

            }

        }

        allPoints.divide(allMasses);

        return allPoints;
    }
}
