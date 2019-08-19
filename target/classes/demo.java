import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.spec.annotations.Given;
import org.test4j.spec.annotations.Named;
import org.test4j.spec.annotations.Then;
import org.test4j.spec.annotations.When;
import org.test4j.spec.inner.IScenario;
import org.test4j.testng.JSpec;
import org.testng.annotations.Test;

public  class demo extends JSpec {
    private Integer var=0;
    @SpringBeanByName
    @Test(dataProvider = "story")
    public void runScenario(IScenario iScenario) throws Throwable {
        this.run(iScenario  );
    }

    @Given
    public void setVar(final @Named("变量值") Integer var)throws Exception{
        this.var=var;

    }

    @When
    public void addVar(final @Named("增量") Integer inc)throws Exception{
        this.var +=inc;
    }

    @Then
    public  void checkVar(final @Named("变量值") Integer expected)throws Exception{
        System.out.println("this.var===="+this.var);
        want.number( this.var ).isEqualTo( expected );

    }
}
