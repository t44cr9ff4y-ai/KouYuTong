# 口语通项目

## 项目结构
- 包名: com.kuayutong
- 架构: MVVM + Room + ViewBinding
- 语言: Kotlin
- 最低 API: 24, 目标/编译 API: 34

## 数据层
- Room 数据库: AppDatabase (sentences, user_sentences, user_progress)
- 句子数据: SentenceData.kt (3个等级已完成: A1_ENTRY, A1_BASIC, A1_ADVANCED，共600句)
- 种子: SentenceSeeder 从 SentenceData 加载数据

## CEFR 等级配置
- 10个等级, 每个10场景×20句子=200句/等级
- 等级图标: ic_level_* (10个不同 vector drawable, 按难度升级)
- 等级枚举: CefrLevel (有 displayName, order, iconRes)

## 已实现的修改
- 修改1: 上一句/下一句按钮, 当前句不可跳到下一句
- 修改2: 标点空格容错 (normalize 函数)
- 修改3: 复杂句子(6+词) 语义匹配 (isSemanticallySimilar)
- 修改4: 2次错误后显示AI提示 (非直接答案)
- 修改5: 10个等级不同图标 (vector drawables)
- 修改6: 用户翻译记录持久化 (UserSentenceEntity)
- 修改7: 主题页进度与练习页同步

## 待扩展
- 7个等级句子数据 (A2~C2): 1400句待添加
- 场景钻取导向练习页
- 连续天数计算已实现但未写回数据库
