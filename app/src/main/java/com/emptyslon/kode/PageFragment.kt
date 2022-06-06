import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.emptyslon.kode.AdapterHeaderCategories
import com.emptyslon.kode.R
import com.emptyslon.kode.databinding.ActivityMainBinding
import com.emptyslon.kode.databinding.FragmentPageBinding

// Here ":" symbol is indicate that LoginFragment
// is child class of Fragment Class

class PageFragment () : Fragment() {

//  val listCategories =
//    listOf<String>("All", "Designers", "Analysts", "Managers", "IOS", "Android")
//  lateinit var binding: FragmentPageBinding
//  lateinit var adapterCategory: AdapterHeaderCategories

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
//    adapterCategory = AdapterHeaderCategories(this.requireContext(), listCategories)
//    binding.recycleFragment.adapter = adapterCategory
//    binding.recycleFragment.layoutManager =
//      LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    return inflater.inflate(
      R.layout.fragment_page, container, false
    )
  }
// Here "layout_login" is a name of layout file
// created for LoginFragment
}