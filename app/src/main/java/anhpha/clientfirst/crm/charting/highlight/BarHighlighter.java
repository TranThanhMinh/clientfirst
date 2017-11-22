package anhpha.clientfirst.crm.charting.highlight;

import anhpha.clientfirst.crm.charting.data.BarData;
import anhpha.clientfirst.crm.charting.data.BarEntry;
import anhpha.clientfirst.crm.charting.data.BarLineScatterCandleBubbleData;
import anhpha.clientfirst.crm.charting.interfaces.dataprovider.BarDataProvider;
import anhpha.clientfirst.crm.charting.interfaces.datasets.IBarDataSet;
import anhpha.clientfirst.crm.charting.utils.MPPointD;

/**
 * Created by Philipp Jahoda on 22/07/15.
 */
public class BarHighlighter extends ChartHighlighter<BarDataProvider> {

    public BarHighlighter(BarDataProvider chart) {
        super(chart);
    }

    @Override
    public anhpha.clientfirst.crm.charting.highlight.Highlight getHighlight(float x, float y) {
        anhpha.clientfirst.crm.charting.highlight.Highlight high = super.getHighlight(x, y);

        if(high == null) {
            return null;
        }

        MPPointD pos = getValsForTouch(x, y);

        BarData barData = mChart.getBarData();

        IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());
        if (set.isStacked()) {

            return getStackedHighlight(high,
                    set,
                    (float) pos.x,
                    (float) pos.y);
        }

        MPPointD.recycleInstance(pos);

        return high;
    }

    /**
     * This method creates the Highlight object that also indicates which value of a stacked BarEntry has been
     * selected.
     *
     * @param high the Highlight to work with looking for stacked values
     * @param set
     * @param xVal
     * @param yVal
     * @return
     */
    public anhpha.clientfirst.crm.charting.highlight.Highlight getStackedHighlight(anhpha.clientfirst.crm.charting.highlight.Highlight high, IBarDataSet set, float xVal, float yVal) {

        BarEntry entry = set.getEntryForXValue(xVal, yVal);

        if (entry == null)
            return null;

        // not stacked
        if (entry.getYVals() == null) {
            return high;
        } else {
            anhpha.clientfirst.crm.charting.highlight.Range[] ranges = entry.getRanges();

            if (ranges.length > 0) {
                int stackIndex = getClosestStackIndex(ranges, yVal);

                MPPointD pixels = mChart.getTransformer(set.getAxisDependency()).getPixelForValues(high.getX(), ranges[stackIndex].to);

                anhpha.clientfirst.crm.charting.highlight.Highlight stackedHigh = new anhpha.clientfirst.crm.charting.highlight.Highlight(
                        entry.getX(),
                        entry.getY(),
                        (float) pixels.x,
                        (float) pixels.y,
                        high.getDataSetIndex(),
                        stackIndex,
                        high.getAxis()
                );

                MPPointD.recycleInstance(pixels);

                return stackedHigh;
            }
        }

        return null;
    }

    /**
     * Returns the index of the closest value inside the values array / ranges (stacked barchart) to the value
     * given as
     * a parameter.
     *
     * @param ranges
     * @param value
     * @return
     */
    protected int getClosestStackIndex(anhpha.clientfirst.crm.charting.highlight.Range[] ranges, float value) {

        if (ranges == null || ranges.length == 0)
            return 0;

        int stackIndex = 0;

        for (Range range : ranges) {
            if (range.contains(value))
                return stackIndex;
            else
                stackIndex++;
        }

        int length = Math.max(ranges.length - 1, 0);

        return (value > ranges[length].to) ? length : 0;
    }

//    /**
//     * Splits up the stack-values of the given bar-entry into Range objects.
//     *
//     * @param entry
//     * @return
//     */
//    protected Range[] getRanges(BarEntry entry) {
//
//        float[] values = entry.getYVals();
//
//        if (values == null || values.length == 0)
//            return new Range[0];
//
//        Range[] ranges = new Range[values.length];
//
//        float negRemain = -entry.getNegativeSum();
//        float posRemain = 0f;
//
//        for (int i = 0; i < ranges.length; i++) {
//
//            float value = values[i];
//
//            if (value < 0) {
//                ranges[i] = new Range(negRemain, negRemain + Math.abs(value));
//                negRemain += Math.abs(value);
//            } else {
//                ranges[i] = new Range(posRemain, posRemain + value);
//                posRemain += value;
//            }
//        }
//
//        return ranges;
//    }

    @Override
    protected float getDistance(float x1, float y1, float x2, float y2) {
        return Math.abs(x1 - x2);
    }

    @Override
    protected BarLineScatterCandleBubbleData getData() {
        return mChart.getBarData();
    }
}
