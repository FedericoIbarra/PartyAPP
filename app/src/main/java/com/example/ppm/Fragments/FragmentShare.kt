package iteso.mx.fragments.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.ppm.R
import com.google.android.material.textfield.TextInputEditText
import com.mikhaellopez.circularimageview.CircularImageView


class FragmentShare : Fragment() {
    private val IMAGE_PICK_CODE = 1000;
    private val PERMISSION_CODE = 1001;

    private lateinit var photo: CircularImageView
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var birthday: TextInputEditText
    private lateinit var addPicture: ImageButton
    private lateinit var share: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_share, container, false)
        photo = view.findViewById(R.id.fragment_profle_profilePhoto)
        username = view.findViewById(R.id.fragment_profle_ti_username_tx)
        email = view.findViewById(R.id.fragment_profle_tf_email_tx)
        birthday = view.findViewById(R.id.fragment_profile_tf_birthday_tx)
        addPicture = view.findViewById(R.id.fragment_profle_tf_getPhoto)
        share = view.findViewById(R.id.fragment_profile_tf_shareProfile)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Add photo
        addPicture.setOnClickListener(View.OnClickListener {
            Log.d("Listener", "Add photo")
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(activity!!.applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }

        })

        //Share
        share.setOnClickListener(View.OnClickListener {
            Log.d("Listener", "Share")
        })
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    /*//handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(activity!!.applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            photo.setImageURI(data?.data)
        }
    }
}
