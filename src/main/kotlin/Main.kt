import com.google.common.hash.Hashing
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

fun main(args: Array<String>) {
    val parser = ArgParser("program")
    val file by parser.option(
        type = ArgType.String,
        fullName = "file",
        shortName = "f",
        description = "Input file"
    ).required()
    val amount by parser.option(
        type = ArgType.Int,
        fullName = "numbilets",
        shortName = "n",
        description = "Tickets amount"
    ).required()
    val param by parser.option(
        type = ArgType.Int,
        fullName = "parameter",
        shortName = "p",
        description = "Distribution param"
    ).required()

    parser.parse(args)

    val generator = TicketGenerator(
        hashFunction = Hashing.sha256(),
        salt = param,
        range = 1U..amount.toUInt(),
    )

    Files.lines(Path.of(file)).asSequence().map {
        "$it: ${generator.generate(it)}"
    }.forEach(::println)
}