<template>
Hello:<el-input style="width: 300px;" class="class1"></el-input>
  Hello2:<el-input style="width: 300px;" class="class2"></el-input>
</template>

<script setup lang="ts" name="Meeting">

</script>

<style scoped>
.class1:deep(.el-input__wrapper) {
  box-shadow: 0 0 0 0px var(--el-input-border-color, var(--el-border-color)) inset;
  cursor: default;
}
</style>