package br.com.devteam.relative.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.devteam.relative.R
import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.view.fragment.CategoryFragment
import br.com.devteam.relative.viewmodel.CategoryViewModel
import br.com.devteam.relative.viewmodel.UserCategory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card_list_item.view.ivPoster
import kotlinx.android.synthetic.main.category_card_list_item.view.tvTitle
import kotlinx.android.synthetic.main.category_list_lateral_item.view.*
import kotlinx.android.synthetic.main.category_card_list_item.view.*
import kotlinx.android.synthetic.main.category_card_list_item.view.ivPoster
import kotlinx.android.synthetic.main.category_card_list_item.view.tvTitle
import kotlinx.android.synthetic.main.category_list_lateral_item.view.*

class SearchAdapter(
    private val dataSet: List<UserCategory>,
    private val viewModel: CategoryViewModel,
    private val onClickItemCallback: (category: Category) -> Unit,
    private val onCheckedChangeCallback: (userCategory: UserCategory, value: Boolean) -> Unit
) :

    RecyclerView.Adapter<SearchAdapter.CategoryViewHolderCard>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolderCard {
        var view: View? = null
        if (viewType == 1) {
            view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_card_list_item, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_lateral_item, parent, false)
        }

        var viewHolder: CategoryViewHolderCard? = null

        if (viewType == 1) {
            viewHolder = CategoryViewHolderCard(view)
        } else {
            viewHolder = CategoryViewHolder(view)
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            // TODO: Remove after test
            return 2
        } else {
            return 2
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolderCard, position: Int) {
        val userCategory = dataSet[position]
//        val category = dataSet[position].category
        val checked = dataSet[position].checked

        holder.title.text = userCategory.category.name
        holder.itemView.setOnClickListener {
            Log.println(Log.INFO, "CLICK", userCategory.category?.id + "")
            onClickItemCallback(userCategory.category)
        }

        holder.itemView.sw_check_category.isChecked = checked

        holder.itemView.sw_check_category.setOnClickListener {
            onCheckedChangeCallback(userCategory, it.sw_check_category.isChecked )
        }

        if (holder is CategoryViewHolder) {
            holder.subtitle.text = userCategory.category.description
        }

        if (userCategory.category.card_image != null) {
            viewModel.getCategoryCardImageUrl(userCategory.category.card_image!!) {
                if (it!!.success) {
                    Picasso.get().load(it.data).into(holder.poster);
                }
            }
        }
    }

    open class CategoryViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.ivPoster
        val title: TextView = itemView.tvTitle
    }

    class CategoryViewHolder(itemView: View) : CategoryViewHolderCard(itemView) {
        val subtitle: TextView = itemView.tvSubtitle
        val sw_check_category: Switch = itemView.sw_check_category
    }

}