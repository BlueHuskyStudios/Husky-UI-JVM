package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import org.bh.tools.base.math.geometry.*
import java.awt.Rectangle
import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.PathIterator
import java.awt.geom.PathIterator.*
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

/**
 * Converts this fractionally-measured rectangle into an AWT shape
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
val FractionRect.awtShapeValue: Shape get() = this.awtValue

val FractionLineSegment.awtShapeValue: Shape get() = object : Shape { // TODO: Can this be simplified? Like, with an API call?

    private val line: FractionLineSegment get() = this@awtShapeValue


    override fun contains(x: Double, y: Double): Boolean = contains(FractionPoint(x, y))


    override fun contains(p: Point2D?): Boolean = p != null && contains(FractionPoint(p))


    fun contains(point: ComputablePoint<Fraction>): Boolean = line.contains(point)


    override fun contains(x: Double, y: Double, width: Double, height: Double): Boolean = contains(FractionRect(x, y, width, height))


    override fun contains(r: Rectangle2D?): Boolean = r != null && contains(FractionRect(r))


    fun contains(rect: FractionRect): Boolean = rect.height == 0.0 && rect.width == 0.0 && contains(rect.origin)


    override fun getBounds2D(): Rectangle2D = line.bounds.awtValue

    // TODO: Test to ensure this works as expected
    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return getPathIterator(at, flatness = defaultFloat64CalculationTolerance)
    }

    override fun getPathIterator(at: AffineTransform?, flatness: Float64): PathIterator =
            object : PathIterator {

                private val transformedLine = at?.let { transformed(it) }
                private var isEndPoint = false
                private var processedEndPoint = false

                override fun next() {
                    isEndPoint = true
                }

                override fun isDone(): Boolean {
                    return processedEndPoint
                }

                override fun getWindingRule() = WIND_NON_ZERO

                override fun currentSegment(coords: FloatArray?): Int {
                    if (coords == null) {
                        return SEG_CLOSE
                    }

                    coords[0] = (if (isEndPoint) end else start).x.float32Value
                    coords[1] = (if (isEndPoint) end else start).y.float32Value

                    return if (isEndPoint) {
                        processedEndPoint = true
                        SEG_LINETO
                    }
                    else {
                        SEG_MOVETO
                    }
                }

                override fun currentSegment(coords: DoubleArray?): Int {
                    if (coords == null) {
                        return SEG_CLOSE
                    }

                    coords[0] = (if (isEndPoint) end else start).x
                    coords[1] = (if (isEndPoint) end else start).y

                    return if (isEndPoint) {
                        processedEndPoint = true
                        SEG_LINETO
                    }
                    else {
                        SEG_MOVETO
                    }
                }
            }

    fun transformed(at: AffineTransform): Shape {
        println("TODO: Actually transform")
        return this
    }

    override fun intersects(x: Double, y: Double, width: Double, height: Double): Boolean
            = intersects(FractionRect(x = x, y = y, width = width, height = height))

    override fun intersects(r: Rectangle2D?): Boolean
            = r != null && intersects(FractionRect(r))

    fun intersects(r: FractionRect): Boolean
            = line.intersects(FractionLineSegment(r.minXminY, r.maxXmaxY))
            || line.intersects(FractionLineSegment(r.maxXminY, r.minXmaxY))

    override fun getBounds(): Rectangle = line.bounds.awtValue.bounds

}
