<script setup lang="ts">
import { defineProps, onMounted, ref } from "vue";
import axios from "axios";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  },
});

const post = ref({
  id: 0,
  title: "",
  content: "",
});

// onMounted(() =>{
//   console.log(props.postId)
//   axios.get('/api/posts'/${props.postId).then((response) => {
//     post.value = response.data;
//   });
// });
</script>

<template>
  <h2>{{ post.title }}</h2>
  <div>{{ post.content }}</div>

  <el-button>수정</el-button>
</template>
