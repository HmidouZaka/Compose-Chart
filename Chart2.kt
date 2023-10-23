@OptIn(ExperimentalTextApi::class)
@Composable
fun ChartExample2(modifier: Modifier, chartProjects: ChartProjects) {
    val textColor = MaterialTheme.colorScheme.scrim
    val textM = rememberTextMeasurer()
    val textSize = with(LocalDensity.current) { 14.sp.toPx() }
    val widthBox = with(LocalDensity.current) { 75.dp.toPx() }

    Canvas(modifier = modifier.padding(vertical = 8.dp)) {
        val padding = 60f
        val presentOfDone =
            chartProjects.numberOfDoneProjects.toFloat() / chartProjects.totalProjects.toFloat()
        val presentOfWaiting =
            chartProjects.numberOfWaitingProjects.toFloat() / chartProjects.totalProjects.toFloat()
        val presentOfToDo =
            chartProjects.numberOfTodoProjects.toFloat() / chartProjects.totalProjects.toFloat()
        val realHeight = size.height - padding - ((size.height - padding) * 0.2f)
        var xDone = (realHeight - realHeight * presentOfDone) + ((size.height - padding) * 0.2f)
        var xWaiting =
            (realHeight - realHeight * presentOfWaiting) + ((size.height - padding) * 0.2f)
        var xTodo = (realHeight - realHeight * presentOfToDo) + ((size.height - padding) * 0.2f)


        val dataPosition = HashMap<Float, Float>()
        dataPosition[0.2f] = 1.0f
        dataPosition[0.4f] = 0.75f
        dataPosition[0.6f] = 0.5f
        dataPosition[0.8f] = 0.25f
        dataPosition[1.0f] = 0f



        for ((position, target) in dataPosition) {
            drawText(
                textMeasurer = textM, text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 14.sp)) {
                        val number = (target * chartProjects.totalProjects).toInt()
                        if (number > 10) {
                            append(number.getRealNumber())
                        } else {
                            append("0" + number.getRealNumber())
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
        drawLine(
            Color.Gray,
            Offset(70f, padding),
            Offset(70f, size.height - padding),
            strokeWidth = 2f
        )
        drawLine(
            textColor,
            Offset(70f, size.height - padding),
            Offset(size.width, size.height - padding),
            strokeWidth = 2f
        )
        for (i in arrayOf(0.22f to "done", 0.55f to "waiting", 0.93f to "to do")) {
            drawText(
                textMeasurer = textM, text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 14.sp)) {
                        append(i.second)
                    }
                },
                topLeft = Offset(
                    (size.width - 150) * i.first, (size.height - padding)
                )
            )
        }


        var index = 0
        for (i in arrayOf(0.22f to xDone, 0.53f to xWaiting, 0.84f to xTodo)) {
            drawText(
                textMeasurer = textM, text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 14.sp)) {
                        if (index == 0) {
                            append(
                                chartProjects.numberOfDoneProjects.toString().addZeroIfHaveOnChar()
                            )
                        } else if (index == 1) {
                            append(
                                chartProjects.numberOfWaitingProjects.toString()
                                    .addZeroIfHaveOnChar()
                            )
                        } else {
                            append(
                                chartProjects.numberOfTodoProjects.toString().addZeroIfHaveOnChar()
                            )
                            index = 0
                        }
                    }
                },
                topLeft = Offset(
                    size.width * i.first - (widthBox * 0.25f - (textSize * 0.5f)),
                    i.second - (padding)
                )
            )
            ++index
            val path = Path()
            path.moveTo((size.width * i.first) - widthBox * 0.5f, i.second)
            path.lineTo((size.width * i.first) - widthBox * 0.5f, size.height - padding - 2f)
            path.lineTo(
                (size.width * i.first) + widthBox - widthBox * 0.5f,
                size.height - padding - 2f
            )
            path.lineTo((size.width * i.first) + widthBox - widthBox * 0.5f, i.second)
            drawPath(path = path, color = Color(0xFF03A9F4))
        }

    }
}