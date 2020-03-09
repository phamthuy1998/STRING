package thuy.ptithcm.string.features.user.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import thuy.ptithcm.string.R
import kotlinx.android.synthetic.main.activity_interests.*
import thuy.ptithcm.string.features.user.adapter.InterestAdapter
import thuy.ptithcm.string.features.user.model.Interest
import thuy.ptithcm.string.support.GridItemDecoration

class InterestActivity : AppCompatActivity(){

    companion object {
        private var instance: InterestActivity? = null
        fun getInstance(): InterestActivity {
            if (instance == null) instance =
                InterestActivity()
            return instance!!
        }
    }

    private val storyAdapter: InterestAdapter by lazy {
        InterestAdapter { interest ->
            itemInterestClick(interest)
        }
    }

    private fun itemInterestClick(interest: Interest) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        inItView()
    }

    private fun inItView() {
        rv_interests.adapter = storyAdapter
        rv_interests.layoutManager = GridLayoutManager(this, 2)
        rv_interests.addItemDecoration(GridItemDecoration(10, 2))
    }

}
