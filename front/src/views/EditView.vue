<script setup lang="ts">

const router = useRouter();


</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15" />
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="write()">글 작성 완료</el-button>
  </div>
</template>

<style></style>
