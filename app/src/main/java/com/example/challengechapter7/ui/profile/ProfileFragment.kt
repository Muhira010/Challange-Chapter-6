package com.example.challengechapter7.ui.profile

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentProfileBinding
import com.example.challengechapter7.ui.MainActivity
import com.example.challengechapter7.ui.signin.SignInActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        loadUser()

        binding.rivProfileUser.setOnClickListener {
            showImageMenu()
        }

        binding.ivLogout.setOnClickListener {
            logout()
        }

        binding.btnUpdate.setOnClickListener {
            validateUser()
        }

        binding.cardBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_mainMovieFragment)
        }
    }

    private var name = ""
    private fun validateUser() {
        name = binding.etNameUser.text.toString()

        if (name.isEmpty()){
            Toast.makeText(context, "masukan nama", Toast.LENGTH_SHORT).show()
        }else{
            if (imageUri == null){
                updateProfile("")
            }else{
                uploadImage()
            }
        }
    }

    private fun updateProfile(uploadedImageUrl: String) {
        val name = binding.etNameUser.text.toString()
        val email = binding.etEmaileUser.text.toString()

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["fullName"] = name
        hashMap["email"] = email
        if (imageUri != null){
            hashMap["photo"] = uploadedImageUrl
        }

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "berhasil update", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "gagal update", Toast.LENGTH_SHORT).show()
            }

    }

    private fun uploadImage() {
        val filePathName = "ProfileImages/" + auth.uid

        val ref = FirebaseStorage.getInstance().getReference(filePathName)
        ref.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                updateProfile(uploadedImageUrl)
            }
            .addOnFailureListener{
                Toast.makeText(context, "gagal untuk upload image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showImageMenu(){
        val popupMenu = PopupMenu(context, binding.rivProfileUser)
        popupMenu.menu.add(Menu.NONE, 0,0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1,1, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == 0){
                pickCamera()
            }else if (id == 1){
                pickGallery()
            }
            true
        }
    }

    private fun pickCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        val resolver = requireActivity().contentResolver
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private fun pickGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
//                imageUri = data!!.data

                binding.rivProfileUser.setImageURI(imageUri)
            }else{
                Toast.makeText(context, "telah dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageUri = data!!.data

                binding.rivProfileUser.setImageURI(imageUri)
            }else{
                Toast.makeText(context, "telah dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun loadUser(){
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("fullName").value}"
                    val email = "${snapshot.child("email").value}"
                    val photo = "${snapshot.child("photo").value}"

                    binding.etNameUser.setText(name)
                    binding.etEmaileUser.setText(email)
                    Glide.with(this@ProfileFragment)
                        .load(photo)
                        .placeholder(R.drawable.placeholder3)
                        .into(binding.rivProfileUser)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun logout() {
        auth.signOut()
        Intent(activity, SignInActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }
}