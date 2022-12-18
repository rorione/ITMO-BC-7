import com.google.common.hash.HashFunction

interface Generator<I, O> {
    fun generate(input: I): O
}

class TicketGenerator(
    private val hashFunction: HashFunction,
    private val salt: Int,
    private val range: UIntRange,
) : Generator<String, ULong> {

    @Suppress("UnstableApiUsage")
    override fun generate(input: String): ULong {
        val hasher = hashFunction.newHasher()

        val code = hasher
            .putInt(salt)
            .putString(input, Charsets.UTF_8)
            .hash()

        return (code.asLong().toULong() % range.last) + range.first
    }
}