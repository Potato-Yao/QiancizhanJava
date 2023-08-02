import com.potato.Config
import com.potato.OCRUtil.OCRReader
import org.junit.jupiter.api.Test
import java.io.File

class OCRTest
{
    @Test
    fun test()
    {
        Config.initial()
        val tester = OCRReader()
        val a = tester.recognizeWordList(File("./QiancizhanKernel/src/test/resources", "111.png"))

        a.forEach {
            println(it)
        }
    }
}