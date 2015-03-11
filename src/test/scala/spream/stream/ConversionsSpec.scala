package spream.stream

import org.specs2.Specification


class ConversionsSpec extends Specification with IteratorConversions {
  def is = s2"""

    Iterator conversions ...

    iteratorToProcess should work on
      simple case                   $t1
      empty case                    $t2

    process1ToIterators should
      work last                     $t3
      work chunk                    $t4
    """

  def t1 = iteratorToProcess0((1 to 10).iterator).toList must_== (1 to 10)

  def t2 = iteratorToProcess0(Nil.iterator).toList must_== Nil

  def t3 = {
    val p = scalaz.stream.process1.last[Int]
    val f = process1ToIterators(p) _
    f((1 to 10).iterator).toList must_== List(10)
  }

  def t4 = {
    val p = scalaz.stream.process1.chunk[Int](2)
    val f = process1ToIterators(p) _
    f((1 to 10).iterator).toList must_== (1 to 5).map(x => Vector(2*x-1,2*x))
  }


}

