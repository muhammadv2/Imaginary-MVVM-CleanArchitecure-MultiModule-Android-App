import androidx.annotation.VisibleForTesting
import com.developance.network.fake.FakeAssetManager
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.util.*
/**
 * This class helps with loading Android `/assets` files, especially when running JVM unit tests.
 * It must remain on the root package for an easier [Class.getResource] with relative paths.
 * @see <a href="https://developer.android.com/reference/tools/gradle-api/7.3/com/android/build/api/dsl/UnitTestOptions">UnitTestOptions</a>
 */
@VisibleForTesting
internal object JvmUnitTestFakeAssetManager : FakeAssetManager {
    private val config =
        requireNotNull(javaClass.getResource("com/android/tools/test_config.properties")) {
            """
            Missing Android resources properties file.
            Did you forget to enable the feature in the gradle build file?
            android.testOptions.unitTests.isIncludeAndroidResources = true
            """.trimIndent()
        }
    private val properties = Properties().apply { config.openStream().use(::load) }
    private val assets = File(properties["android_merged_assets"].toString())

    override fun open(fileName: String): BufferedReader =
        File(assets, fileName).inputStream().bufferedReader()
}
