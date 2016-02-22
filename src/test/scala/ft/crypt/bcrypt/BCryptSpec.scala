package ft.crypt.bcrypt

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{ FlatSpec, MustMatchers, PrivateMethodTester }

// Copyright (c) 2006 Damien Miller <djm@mindrot.org>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

/**
 * Ported from https://github.com/jeremyh/jBCrypt/blob/master/src/test/java/org/mindrot/TestBCrypt.java
 *
 * @author Ricardo Leon
 * @since 2/16/16
 */
class BCryptSpec extends FlatSpec with PrivateMethodTester with PropertyChecks with MustMatchers {
  private[this] val encodeBase64 = PrivateMethod[String]('encodeBase64)
  private[this] val decodeBase64 = PrivateMethod[Array[Byte]]('decodeBase64)
  /**
   * Plain, Salt, Expected
   */
  private[this] val testVectors = Seq(
    ("", "$2a$06$DCq7YPn5Rq63x1Lad4cll.", "$2a$06$DCq7YPn5Rq63x1Lad4cll.TV4S6ytwfsfvkgY8jIucDrjc8deX1s."),
    ("", "$2a$08$HqWuK6/Ng6sg9gQzbLrgb.", "$2a$08$HqWuK6/Ng6sg9gQzbLrgb.Tl.ZHfXLhvt/SgVyWhQqgqcZ7ZuUtye"),
    ("", "$2a$10$k1wbIrmNyFAPwPVPSVa/ze", "$2a$10$k1wbIrmNyFAPwPVPSVa/zecw2BCEnBwVS2GbrmgzxFUOqW9dk4TCW"),
    ("", "$2a$12$k42ZFHFWqBp3vWli.nIn8u", "$2a$12$k42ZFHFWqBp3vWli.nIn8uYyIkbvYRvodzbfbK18SSsY.CsIQPlxO"),
    ("a", "$2a$06$m0CrhHm10qJ3lXRY.5zDGO", "$2a$06$m0CrhHm10qJ3lXRY.5zDGO3rS2KdeeWLuGmsfGlMfOxih58VYVfxe"),
    ("a", "$2a$08$cfcvVd2aQ8CMvoMpP2EBfe", "$2a$08$cfcvVd2aQ8CMvoMpP2EBfeodLEkkFJ9umNEfPD18.hUF62qqlC/V."),
    ("a", "$2a$10$k87L/MF28Q673VKh8/cPi.", "$2a$10$k87L/MF28Q673VKh8/cPi.SUl7MU/rWuSiIDDFayrKk/1tBsSQu4u"),
    ("a", "$2a$12$8NJH3LsPrANStV6XtBakCe", "$2a$12$8NJH3LsPrANStV6XtBakCez0cKHXVxmvxIlcz785vxAIZrihHZpeS"),
    ("abc", "$2a$06$If6bvum7DFjUnE9p2uDeDu", "$2a$06$If6bvum7DFjUnE9p2uDeDu0YHzrHM6tf.iqN8.yx.jNN1ILEf7h0i"),
    ("abc", "$2a$08$Ro0CUfOqk6cXEKf3dyaM7O", "$2a$08$Ro0CUfOqk6cXEKf3dyaM7OhSCvnwM9s4wIX9JeLapehKK5YdLxKcm"),
    ("abc", "$2a$10$WvvTPHKwdBJ3uk0Z37EMR.", "$2a$10$WvvTPHKwdBJ3uk0Z37EMR.hLA2W6N9AEBhEgrAOljy2Ae5MtaSIUi"),
    ("abc", "$2a$12$EXRkfkdmXn2gzds2SSitu.", "$2a$12$EXRkfkdmXn2gzds2SSitu.MW9.gAVqa9eLS1//RYtYCmB1eLHg.9q"),
    ("abcdefghijklmnopqrstuvwxyz", "$2a$06$.rCVZVOThsIa97pEDOxvGu", "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC"),
    ("abcdefghijklmnopqrstuvwxyz", "$2a$08$aTsUwsyowQuzRrDqFflhge", "$2a$08$aTsUwsyowQuzRrDqFflhgekJ8d9/7Z3GV3UcgvzQW3J5zMyrTvlz."),
    ("abcdefghijklmnopqrstuvwxyz", "$2a$10$fVH8e28OQRj9tqiDXs1e1u", "$2a$10$fVH8e28OQRj9tqiDXs1e1uxpsjN0c7II7YPKXua2NAKYvM6iQk7dq"),
    ("abcdefghijklmnopqrstuvwxyz", "$2a$12$D4G5f18o7aMMfwasBL7Gpu", "$2a$12$D4G5f18o7aMMfwasBL7GpuQWuP3pkrZrOAnqP.bmezbMng.QwJ/pG"),
    ("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$06$fPIsBO8qRqkjj273rfaOI.", "$2a$06$fPIsBO8qRqkjj273rfaOI.HtSV9jLDpTbZn782DC6/t7qT67P6FfO"),
    ("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$08$Eq2r4G/76Wv39MzSX262hu", "$2a$08$Eq2r4G/76Wv39MzSX262huzPz612MZiYHVUJe/OcOql2jo4.9UxTW"),
    ("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$10$LgfYWkbzEvQ4JakH7rOvHe", "$2a$10$LgfYWkbzEvQ4JakH7rOvHe0y8pHKF9OaFgwUZ2q7W2FFZmZzJYlfS"),
    ("~!@#$%^&*()      ~!@#$%^&*()PNBFRD", "$2a$12$WApznUOJfkEGSmYRfnkrPO", "$2a$12$WApznUOJfkEGSmYRfnkrPOr466oFDCaj4b6HY3EXGvfxm43seyhgC"),
    ("Hello", "$2a$04$Ple0QwxiRAJSclOT8yq4r.", "$2a$04$Ple0QwxiRAJSclOT8yq4r.SuhyoAaZtEOS64rgLQRsVaJJ3UnXT4u")
  )

  behavior of "BCrypt"

  it should "hash the passwords" in {
    forAll(Gen.oneOf(testVectors)) {
      case (plain, salt, expected) =>
        val hashed = BCrypt.hashPassword(plain, salt)
        assert(BCrypt.checkPassword(plain, hashed))
        assert(hashed == expected)
    }
  }

  it should "encode and decode to reach the same result" in {
    forAll {
      input: Array[Byte] =>
        val encoded = BCrypt invokePrivate encodeBase64(input, input.length)
        val decoded = BCrypt invokePrivate decodeBase64(encoded, input.length)
        decoded must equal(input)
    }
  }

  it should "encode 0 -1" in {
    val encoded = BCrypt invokePrivate encodeBase64(Array[Byte](0, -1), 2)
    assert(encoded == ".N6")
  }

  it should "decode .N6" in {
    val decoded = BCrypt invokePrivate decodeBase64(".N6", 2)
    decoded must equal(Array[Byte](0, -1))
  }

  it should "decode dljl" in {
    val decoded = BCrypt invokePrivate decodeBase64("dljl", 3)
    decoded must equal(Array[Byte](126, 121, 103))
  }

  it should "not match incorrect passwords hashes" in {
    forAll((Gen.oneOf(testVectors), "(plain1, salt1, expected1)"), (Gen.oneOf(testVectors), "(plain2, salt2, expected2)")) {
      case ((plain1, salt1, expected1), (plain2, salt2, expected2)) =>
        whenever(plain1 != plain2) {
          BCrypt.checkPassword(plain1, expected2) must be(false)
          BCrypt.checkPassword(plain2, expected1) must be(false)
        }
    }
  }
}