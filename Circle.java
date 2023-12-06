package RainDropMaths;

public class Circle {
    private int Id;
    private int x;
    private int y;
    private int ans;
    private String problem;

    public Circle(int id,int x,int y,int ans,String problem){
        this.Id=id;
        this.x=x;
        this.y=y;
        this.ans=ans;
        this.problem=problem;
    }

  //  public void setId(int id) {   this.Id = id;    }

    public int getId(){
        return this.Id;
    }


    public int getY() { return y;    }

    //public void setX(int x) {   this.x = x;    }


    public int getX() {        return x;    }

    public void setY(int y){    this.y=y;   }

    public int getAns() {        return ans;    }

   // public void setAns(int ans) {        this.ans = ans;    }

    public String getProblem() {        return problem;    }

   // public void setProblem(String problem) {        this.problem = problem;    }
}
