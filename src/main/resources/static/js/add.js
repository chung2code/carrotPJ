$(document).ready(function () {
  const formData = new FormData(); // 폼 관련 저장

  const uploadBox_el = document.querySelector(".upload-box");

  // dragenter / dragover /dragleave / drop 이벤트 핸들링
  uploadBox_el.addEventListener("dragenter", function (e) {
    e.preventDefault();
    console.log("dragenter...");
  });

  uploadBox_el.addEventListener("dragover", function (e) {
    e.preventDefault();
    uploadBox_el.style.opacity = "0.5";
    console.log("dragover..");
  });

  uploadBox_el.addEventListener("dragleave", function (e) {
    e.preventDefault();
    uploadBox_el.style.opacity = "1";
    console.log("dragleave...");
  });

  uploadBox_el.addEventListener("drop", function (e) {
    e.preventDefault();

    // 유효성 체크 - 이미지 파일만 처리
    const imageFiles = Array.from(e.dataTransfer.files).filter((file) =>
      file.type.startsWith("image/")
    );
    if (imageFiles.length === 0) {
      alert("이미지 파일만 가능합니다");
      return;
    }

    // 미리보기 생성
    const file = imageFiles[0];
    formData.append("files", file); // formData에 저장

    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onload = function (event) {
      const preview = document.getElementById("preview");
      const imgEl = document.createElement("img");
      imgEl.setAttribute("src", event.target.result);
      preview.appendChild(imgEl);
    };
  });

  $("#addform").submit(function (event) {
    event.preventDefault();

    var titleValue = document.imageform.title.value;
    var detailsValue = document.imageform.details.value;
    var priceValue = document.imageform.price.value;
    var placeValue = document.imageform.place.value;

    formData.append("title", titleValue);
    formData.append("details", detailsValue);
    formData.append("price", priceValue);
    formData.append("place", placeValue);



      $.ajax({
        type: "POST",
        url: "/user/product/add",
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
          alert("성공적으로 게시물이 추가되었습니다.");
          window.location.href = "/user/product/list";
        },
        error: function (jqXHR, textStatus, errorThrown) {
          alert("게시물 추가 실패 : " + textStatus);
          console.error(errorThrown);
        },
      });
  });
