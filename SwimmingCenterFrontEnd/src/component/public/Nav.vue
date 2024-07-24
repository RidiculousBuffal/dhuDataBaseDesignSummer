<script setup>
const props = defineProps({
  'routes': {
    type: Array,
    default: () => []
  }
})
</script>

<template>
  <template v-for="(itm,idx) in routes">
    <el-sub-menu v-if="itm.children.length>0" :index="idx.toString()">
      <template #title>
        {{ itm.meta.title }}
      </template>
      <Nav :routes=itm.children></Nav>

    </el-sub-menu>
    <el-menu-item :index="{name:itm.name}" v-else>
      <span>{{ itm.meta.title }}</span>
    </el-menu-item>
  </template>

</template>

<style scoped lang="scss">

</style>