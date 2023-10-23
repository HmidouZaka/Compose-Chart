@OptIn(ExperimentalTextApi::class)
@Composable
fun ChartExample1(
    modifier: Modifier,
    hashMapData: HashMap<String, Int>,
) {
    val textColor = MaterialTheme.colorScheme.scrim
    val padding = 60f
    val textSize = with(LocalDensity.current) { 14.sp.toPx() }
    val textM = rememberTextMeasurer()
    val allM =
        arrayOf("Jan", "Fab", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    Canvas(
        modifier = modifier.padding(top = 30.dp)
    ) {


        val customHeight = size.height - padding
        drawLine(
            Color.Gray,
            Offset(70f, 0f),
            Offset(70f, customHeight),
            strokeWidth = 2f
        )
        drawLine(
            textColor,
            Offset(70f, customHeight),
            Offset(size.width, customHeight),
            strokeWidth = 2f
        )

        val y = size.width / 12f

        var counter = 1f
        for (m in allM) {
            drawText(
                textMeasurer = textM, text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 12.sp)) {
                        append(m)
                    }
                },
                topLeft = Offset(y * counter, size.height - padding)
            )
            counter = (counter + 0.9f)
        }

        val dataPosition = HashMap<Float, Float>()
        dataPosition[0.2f] = 1.0f
        dataPosition[0.6f] = 0.5f
        dataPosition[1.0f] = 0f

        var max = hashMapData.values.max()
        for ((position, target) in dataPosition) {
            drawText(
                textMeasurer = textM, text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 14.sp)) {
                        if (max > 10) {
                            append((max * target).toInt().getRealNumber())
                        } else {
                            append("0" + (max * target).toInt().getRealNumber())
                        }
                    }
                },
                topLeft = Offset(10f, ((size.height - padding) * position) - textSize * 0.7f)
            )
            if (position != 1f) {
                drawLine(
                    Color.Gray,
                    Offset(70f, (size.height - padding) * position),
                    Offset(size.width, (size.height - padding) * position),
                    strokeWidth = 2f
                )
            }
        }


        var position = 1f
        val realHeight = size.height - padding - ((size.height - padding) * 0.2f)
        var startPaintX = 70f
        var startPaintY = size.height - padding
        for (target in allM.indices) {
            val month = allM[target]
            val numberState = hashMapData.get(month)

            val Y = realHeight - realHeight * (numberState?.toFloat()?.div(max) ?: 1f)

            val resultY = (Y + ((size.height - padding) * 0.2f))
            val resultX = (y + (textSize * 0.3f)) * position


            drawCircle(
                color = Color(0xFFD000FF),
                radius = textSize * 0.2f,
                center = center.copy(
                    x = resultX,
                    y = resultY
                )
            )

            position = (position + 0.9f)

            drawLine(
                color = Color(0xFF2196F3),
                start = Offset(startPaintX,startPaintY),
                end = Offset(resultX,resultY),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFFFF8AFD),
                start = Offset(70f,size.height - padding),
                end = Offset(resultX,resultY),
                cap = StrokeCap.Round
            )
            startPaintX = resultX
            startPaintY = resultY

        }

    }

}