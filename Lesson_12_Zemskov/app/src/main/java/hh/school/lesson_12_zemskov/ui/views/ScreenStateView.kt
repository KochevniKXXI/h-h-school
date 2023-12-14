package hh.school.lesson_12_zemskov.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import hh.school.lesson_12_zemskov.databinding.ViewScreenStateBinding

/**
 * Позволяет отобажать состояния экрана: «Загрузка» и «Ошибка».
 */
class ScreenStateView : FrameLayout {
    private val binding =
        ViewScreenStateBinding.inflate(LayoutInflater.from(context), this)
    private var loadingState = false
    private var errorState = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Устанавливает состояние «Загрузка» и выключает состояние «Ошибка».
     *
     * @param isLoading true — отобразить, false — скрыть.
     */
    fun setLoadingState(isLoading: Boolean) {
        binding.errorStateView.isVisible = false
        loadingState = isLoading
        binding.loadingStateView.isVisible = loadingState
        binding.root.isVisible = loadingState
    }

    /**
     * Устанавливает состояние «Ошибка» и выключает состояние «Загрузка».
     * Чтобы скрыть состояние – передать в [message] значение null,
     * остальные параметры оставить без изменений.
     *
     * @param message сообщение об ошибке.
     * @param buttonText текст кнопки «Повтор».
     * @param onRetryClick функция, выполняемая при нажатии кнопки «Повтор».
     */
    fun setErrorState(
        message: String?,
        buttonText: String? = null,
        onRetryClick: OnClickListener? = null
    ) {
        binding.loadingStateView.isVisible = false
        errorState = message != null || buttonText != null
        binding.textViewMessage.text = message.orEmpty()
        binding.buttonRetry.text = buttonText.orEmpty()
        binding.textViewMessage.isVisible = message != null
        binding.buttonRetry.isVisible = buttonText != null
        binding.buttonRetry.setOnClickListener(onRetryClick)
        binding.errorStateView.isVisible = errorState
        binding.root.isVisible = errorState
    }
}