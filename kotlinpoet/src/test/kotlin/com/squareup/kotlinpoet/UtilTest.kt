/*
 * Copyright (C) 2017 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.kotlinpoet

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilTest {
  @Test fun characterLiteral() {
    assertEquals("a", characterLiteralWithoutSingleQuotes('a'))
    assertEquals("b", characterLiteralWithoutSingleQuotes('b'))
    assertEquals("c", characterLiteralWithoutSingleQuotes('c'))
    assertEquals("%", characterLiteralWithoutSingleQuotes('%'))
    // common escapes
    assertEquals("\\b", characterLiteralWithoutSingleQuotes('\b'))
    assertEquals("\\t", characterLiteralWithoutSingleQuotes('\t'))
    assertEquals("\\n", characterLiteralWithoutSingleQuotes('\n'))
    assertEquals("\\u000c", characterLiteralWithoutSingleQuotes('\u000c'))
    assertEquals("\\r", characterLiteralWithoutSingleQuotes('\r'))
    assertEquals("\"", characterLiteralWithoutSingleQuotes('"'))
    assertEquals("\\'", characterLiteralWithoutSingleQuotes('\''))
    assertEquals("\\\\", characterLiteralWithoutSingleQuotes('\\'))
    // octal escapes
    assertEquals("\\u0000", characterLiteralWithoutSingleQuotes('\u0000'))
    assertEquals("\\u0007", characterLiteralWithoutSingleQuotes('\u0007'))
    assertEquals("?", characterLiteralWithoutSingleQuotes('\u003f'))
    assertEquals("\\u007f", characterLiteralWithoutSingleQuotes('\u007f'))
    assertEquals("¿", characterLiteralWithoutSingleQuotes('\u00bf'))
    assertEquals("ÿ", characterLiteralWithoutSingleQuotes('\u00ff'))
    // unicode escapes
    assertEquals("\\u0000", characterLiteralWithoutSingleQuotes('\u0000'))
    assertEquals("\\u0001", characterLiteralWithoutSingleQuotes('\u0001'))
    assertEquals("\\u0002", characterLiteralWithoutSingleQuotes('\u0002'))
    assertEquals("€", characterLiteralWithoutSingleQuotes('\u20AC'))
    assertEquals("☃", characterLiteralWithoutSingleQuotes('\u2603'))
    assertEquals("♠", characterLiteralWithoutSingleQuotes('\u2660'))
    assertEquals("♣", characterLiteralWithoutSingleQuotes('\u2663'))
    assertEquals("♥", characterLiteralWithoutSingleQuotes('\u2665'))
    assertEquals("♦", characterLiteralWithoutSingleQuotes('\u2666'))
    assertEquals("✵", characterLiteralWithoutSingleQuotes('\u2735'))
    assertEquals("✺", characterLiteralWithoutSingleQuotes('\u273A'))
    assertEquals("／", characterLiteralWithoutSingleQuotes('\uFF0F'))
  }

  @Test fun stringLiteral() {
    stringLiteral("abc")
    stringLiteral("♦♥♠♣")
    stringLiteral("€\\t@\\t$", "€\t@\t$")
    assertEquals("\"\"\"\n|abc();\n|def();\n\"\"\".trimMargin()",
        stringLiteralWithQuotes("abc();\ndef();"))
    stringLiteral("This is \\\"quoted\\\"!", "This is \"quoted\"!")
    stringLiteral("e^{i\\\\pi}+1=0", "e^{i\\pi}+1=0")
  }

  @Test fun legalIdentifiers() {
    assertTrue("foo".isIdentifier)
    assertTrue("bAr1".isIdentifier)
    assertFalse("1".isIdentifier)
    assertFalse("♦♥♠♣".isIdentifier)
    assertTrue("`♦♥♠♣`".isIdentifier)
    assertTrue("`  ♣ !`".isIdentifier)
    assertFalse("€".isIdentifier)
    assertTrue("`€`".isIdentifier)
    assertTrue("`1`".isIdentifier)
    assertFalse("```".isIdentifier)
    assertFalse("``".isIdentifier)
    assertFalse("\n".isIdentifier)
    assertFalse("`\n`".isIdentifier)
    assertFalse("\r".isIdentifier)
    assertFalse("`\r`".isIdentifier)
    assertTrue("when".isIdentifier)
    assertTrue("fun".isIdentifier)
    assertFalse("".isIdentifier)
  }

  private fun stringLiteral(string: String) = stringLiteral(string, string)

  private fun stringLiteral(expected: String, value: String)
      = assertEquals("\"$expected\"", stringLiteralWithQuotes(value))
}