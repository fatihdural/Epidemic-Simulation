package model;

public class Individual {
    private Double speed;
    private Double mask;
    private Double socDistance;
    private Double collide;

    private Double layoutX;
    private Double layoutY;
    private Integer direction;
    private Character situation;        // H - Healthy, I - Infected, W - Waited, U - Undraw/Hospital, D - Dead.
    private int lastCollideIndex;

    private Individual(Builder builder){
        this.speed = builder.speed;
        this.mask = builder.mask;
        this.socDistance = builder.socDistance;
        this.collide = builder.collide;
    }

    // builder class
    public static class Builder{
        private Double speed;
        private Double mask;
        private Double socDistance;
        private Double collide;

        public Builder speed(Double x){
            this.speed = x;
            return this;
        }
        public Builder mask(Double x){
            this.mask = x;
            return this;
        }
        public Builder distance(Double x){
            this.socDistance = x;
            return this;
        }
        public Builder collide(Double x){
            this.collide = x;
            return this;
        }

        public Individual build(){
            return new Individual(this);
        }
    }

    // init get functions
    public Double getMask() {
        return mask;
    }
    public Double getSpeed() {
        return speed;
    }
    public Double getSocDistance() {
        return socDistance;
    }
    public Double getCollide() {
        return collide;
    }

    // last collide
    public int getLastCollideIndex() {
        return lastCollideIndex;
    }
    public void setLastCollideIndex(int lastCollideIndex) {
        this.lastCollideIndex = lastCollideIndex;
    }

    // situation
    public Character getSituation() {
        return situation;
    }
    public void setSituation(Character situation) {
        this.situation = situation;
    }

    // direction
    public Integer getDirection() {
        return direction;
    }
    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    // coordinates
    public Double getLayoutX() {
        return layoutX;
    }
    public void setLayoutX(Double layoutX) {
        this.layoutX = layoutX;
    }
    public Double getLayoutY() {
        return layoutY;
    }
    public void setLayoutY(Double layotuY) {
        this.layoutY = layotuY;
    }
}
