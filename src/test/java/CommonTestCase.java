// FIXME UNABLE TO RUN

//import com.intellij.openapi.command.WriteCommandAction;
//import com.intellij.psi.codeStyle.CodeStyleManager;
//import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
//
//import java.util.List;
//
//public class CommonTestCase extends LightJavaCodeInsightFixtureTestCase {
//    @Override
//    protected String getTestDataPath() {
//        return "src/test/testData";
//    }
//
//    public void testFormatter() {
//        myFixture.configureByFile("machine-formatted.Caddyfile");
//        WriteCommandAction.writeCommandAction(getProject()).run(() ->
//                CodeStyleManager.getInstance(getProject()).reformatText(
//                        myFixture.getFile(),
//                        List.of(myFixture.getFile().getTextRange())
//                )
//        );
//        myFixture.checkResultByFile("human-formatted.Caddyfile");
//    }
//}
